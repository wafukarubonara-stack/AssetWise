# AssetWise — 基本設計書

**Architecture & Design Document**
Version 1.0 | February 2026 | 作成者: Eliza

---

## 1. アーキテクチャ概要

AssetWiseはClean Architecture + MVVMパターンを採用し、3層構造（UI層・Domain層・Data層）でアプリケーションを構成する。各層の依存関係は内側（Domain）から外側（UI/Data）への一方向とし、テスタビリティと保守性を確保する。

### 1.1 パッケージ構成

| パッケージ | 層 | 責務 |
|-----------|-----|------|
| ui/ | Presentation | Fragment, ViewModel, Adapter — 画面表示とユーザー操作 |
| domain/model/ | Domain | アプリ内データモデル（UIに渡す形式） |
| domain/repository/ | Domain | Repositoryのインターフェース定義（契約） |
| domain/usecase/ | Domain | ビジネスロジック（1機能 = 1クラス） |
| data/local/entity/ | Data | Room Entityクラス（DBテーブル定義） |
| data/local/dao/ | Data | DAOインターフェース（DBアクセス） |
| data/local/converter/ | Data | TypeConverter（Enum ⇔ String変換） |
| data/remote/api/ | Data | Retrofitサービスインターフェース（API定義） |
| data/remote/dto/ | Data | APIレスポンスのデータモデル |
| data/repository/ | Data | Repositoryの実装クラス |
| di/ | 横断 | 依存性注入の設定 |
| util/ | 横断 | 共通ユーティリティ（フォーマッタ等） |

### 1.2 データフロー

```
UI (Fragment) → ViewModel → UseCase → Repository Interface → Repository Impl → DAO / API Service
```

データの流れは上記の順で処理され、LiveDataにより逆方向にリアクティブに伝播する。APIから取得したデータはRoom DBにキャッシュし、次回以降はキャッシュ優先で表示する（NetworkBoundResource パターン）。

---

## 2. データベース詳細設計

### 2.1 Entity一覧と実装状態

| Entity | テーブル名 | ForeignKey | Index | 実装 |
|--------|-----------|------------|-------|------|
| AccountEntity | accounts | なし | なし | ✅ |
| AssetEntity | assets | accountId → accounts (CASCADE) | accountId | ✅ |
| TransactionEntity | transactions | accountId → accounts (CASCADE), categoryId → categories (SET NULL) | accountId, categoryId | ✅ |
| CategoryEntity | categories | なし | なし | ✅ |
| PriceHistoryEntity | price_history | assetId → assets (CASCADE) | assetId, (assetId+timestamp) UNIQUE | ✅ |
| ExchangeRateEntity | exchange_rates | なし | (from+to) UNIQUE | ✅ |

### 2.2 TypeConverter

RoomはEnumを直接保存できないため、Convertersクラスで以下の変換を行う。

| Enum | 保存形式 | 変換方法 |
|------|----------|----------|
| AccountType | TEXT (String) | Enum.name() ⇔ Enum.valueOf() |
| AssetType | TEXT (String) | Enum.name() ⇔ Enum.valueOf() |
| TransactionType | TEXT (String) | Enum.name() ⇔ Enum.valueOf() |

### 2.3 DAO設計方針

- 全DAOでLiveData<T>を返すクエリを基本とし、UIのリアクティブ更新を実現
- 書き込み操作（insert/update/delete）は戻り値なし（void）、ExecutorServiceで非同期実行
- PriceHistoryDaoとExchangeRateDaoはON CONFLICT REPLACEで重複データを自動更新
- 複合クエリ（日付範囲、カテゴリ別、シンボル検索等）はDAOに定義済み

### 2.4 初期データシード

AssetWiseDatabaseのonCreateコールバックで、8つのデフォルトカテゴリを自動挿入する。

| カテゴリ名 | アイコン | カラー |
|-----------|---------|--------|
| Salary（給与） | ic_work | #4CAF50 |
| Dividend（配当） | ic_trending_up | #2196F3 |
| Interest（利息） | ic_account_balance | #00BCD4 |
| Transfer（振替） | ic_swap_horiz | #FF9800 |
| Fee（手数料） | ic_receipt | #F44336 |
| Investment（投資） | ic_show_chart | #9C27B0 |
| Living（生活費） | ic_home | #795548 |
| Other（その他） | ic_category | #607D8B |

---

## 3. API設計

### 3.1 API一覧

| API | ベースURL | 認証 | レート制限 |
|-----|----------|------|-----------|
| ExchangeRate-API | https://api.exchangerate-api.com/v4/ | APIキー不要（v4） | 1500回/月 |
| Alpha Vantage | https://www.alphavantage.co/query | APIキー必須 | 25回/日（無料） |
| CoinGecko | https://api.coingecko.com/api/v3/ | APIキー不要 | 30回/分 |

### 3.2 Retrofit サービス設計

各APIに対応するRetrofitインターフェースを作成し、Repository経由でアクセスする。

| インターフェース | メソッド | パラメータ | レスポンス |
|----------------|---------|-----------|-----------|
| ExchangeRateService | GET /latest/{base} | base: 基準通貨 | ExchangeRateResponse |
| StockService | GET /query | function, symbol, apikey | StockQuoteResponse |
| CryptoService | GET /simple/price | ids, vs_currencies | CryptoPriceResponse |

### 3.3 キャッシュ戦略

- API取得データは即座にRoom DBに保存
- 次回アクセス時はDB優先で表示し、バックグラウンドでAPI更新
- タイムスタンプで鮮度管理（為替:24h、株価:営業日、暗号資産:15min）
- WorkManagerによる定期更新スケジュール
- オフライン時はキャッシュデータで継続動作

---

## 4. セキュリティ設計

### 4.1 認証フロー

アプリ起動時およびバックグラウンドからの復帰時に認証を要求する。

- 第一認証: BiometricPrompt（指紋/顔認証）
- フォールバック: PIN / パスワード認証
- 自動ロック: アプリがバックグラウンドに移行して一定時間経過後
- 認証状態はメモリ内のみで管理（永続化しない）

### 4.2 データ暗号化

- データベース: SQLCipherによる暗号化（AES-256-CBC）
- SharedPreferences: EncryptedSharedPreferencesによるAPIキー管理
- 暗号化キーはAndroid Keystoreで管理

### 4.3 APIキー管理

- APIキーはlocal.propertiesに保存（Gitにコミットしない）
- BuildConfigを通じてアプリ内で参照
- EncryptedSharedPreferencesで実行時に安全に保持

---

## 5. UI/UXガイドライン

### 5.1 デザイン原則

- Material Design 3 準拠
- ダークテーマ・ライトテーマの両対応
- カラーパレット: グリーン系（財務・成長のイメージ）
- Bottom Navigationによる直感的なナビゲーション
- 金額表示は常に通貨記号付き・桁区切りフォーマット

### 5.2 多言語対応方針

- strings.xml を values/ (英語) と values-ja/ (日本語) に分離
- 数値フォーマット: Locale対応（1,234.56 vs 1.234,56）
- 日付フォーマット: Locale対応（MM/dd/yyyy vs yyyy/MM/dd）
- 通貨フォーマット: NumberFormat.getCurrencyInstance()使用
- アプリ内での言語切替機能（システム設定とは独立）

---

## 改訂履歴

| バージョン | 日付 | 変更内容 | 作成者 |
|-----------|------|----------|--------|
| 1.0 | 2026/02/15 | 初版作成 | Eliza |
