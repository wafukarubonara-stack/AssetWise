# AssetWise — 要件定義書

**Software Requirements Specification**
Version 1.0 | February 2026 | 作成者: Eliza

---

## 1. プロジェクト概要

### 1.1 プロジェクト名
AssetWise（アセットワイズ）— 個人資産トラッカー

### 1.2 プロジェクトの目的
個人が保有する複数の金融口座（銀行、証券、暗号資産、現金等）を一元管理し、リアルタイムのAPI連携により総資産を正確に把握できるAndroidアプリケーションを開発する。

### 1.3 対象ユーザー
複数の金融口座や投資ポートフォリオを持つ個人ユーザー。特に、異なる通貨建ての資産を保有し、全体像を把握したいユーザーを対象とする。

### 1.4 開発背景
前プロジェクト「Memoripass」（パスワードマネージャー）で培ったAndroid開発スキルを基盤に、より高度な技術（Room DBの複雑なリレーション、REST API連携、多言語対応等）を学習・実践するための発展的プロジェクトとして計画された。

---

## 2. システム要件

### 2.1 動作環境

| 項目 | 要件 |
|------|------|
| プラットフォーム | Android |
| 最小SDK | API 26 (Android 8.0 Oreo) |
| ターゲットSDK | API 36 |
| 対応デバイス | スマートフォン・タブレット |
| ネットワーク | インターネット接続（API連携時に必要） |
| ストレージ | ローカルストレージ（Room Database） |

### 2.2 開発環境

| 項目 | 詳細 |
|------|------|
| OS | Ubuntu 22.04 |
| IDE | Android Studio |
| 言語 | Java (JDK 11) |
| ビルドシステム | Gradle (Kotlin DSL) |
| バージョン管理 | Git + GitHub |
| テスト端末 | Google Pixel 9 (Android 15) |

---

## 3. 機能要件

### 3.1 機能一覧

| 機能ID | 機能名 | 優先度 | 対応Phase |
|--------|--------|--------|-----------|
| F-001 | 生体認証 / PINによるアプリロック | 高 | Phase 3 |
| F-002 | 口座の追加・編集・削除（CRUD） | 高 | Phase 5 |
| F-003 | 個別資産の追加・編集・削除 | 高 | Phase 5 |
| F-004 | 取引記録の追加・編集・削除 | 高 | Phase 5 |
| F-005 | 為替レートAPI連携（自動取得） | 高 | Phase 6 |
| F-006 | 株価API連携（自動取得） | 中 | Phase 7 |
| F-007 | 暗号資産価格API連携（自動取得） | 中 | Phase 7 |
| F-008 | ダッシュボード（総資産表示） | 高 | Phase 8 |
| F-009 | 資産配分グラフ（円グラフ） | 中 | Phase 8 |
| F-010 | 資産推移グラフ（折れ線） | 中 | Phase 8 |
| F-011 | 多通貨自動換算 | 高 | Phase 6 |
| F-012 | 日本語・英語切替 | 中 | Phase 9 |
| F-013 | テーマ切替（ライト/ダーク） | 低 | Phase 9 |
| F-014 | データベース暗号化 | 高 | Phase 3 |
| F-015 | 検索・フィルタ・ソート | 中 | Phase 5 |

### 3.2 口座管理機能 (F-002)

ユーザーは以下の種類の口座を登録・管理できる。各口座には名称、種類、通貨、金融機関名、メモを設定可能。

| 口座種類 | 説明 | 例 |
|----------|------|-----|
| BANK | 銀行口座（普通・定期預金等） | 三菱UFJ銀行 普通預金 |
| BROKERAGE | 証券口座 | SBI証券 特定口座 |
| CRYPTO | 暗号資産ウォレット | Coinbase |
| CASH | 現金 | 財布の現金 |
| OTHER | その他 | ポイント口座等 |

### 3.3 資産管理機能 (F-003)

各口座に紐づく個別資産を管理する。株式銘柄、暗号資産コイン、外貨預金等を登録し、保有数量と取得原価を記録する。

| 資産種類 | 説明 | 例 |
|----------|------|-----|
| STOCK | 個別株式 | AAPL (Apple Inc.) |
| ETF | 上場投資信託 | VTI (Vanguard Total Stock) |
| MUTUAL_FUND | 投資信託 | eMAXIS Slim 全世界株式 |
| BOND | 債券 | 日本国債 |
| CRYPTO | 暗号資産 | BTC (Bitcoin) |
| CURRENCY | 外貨預金 | USD 普通預金 |
| DEPOSIT | 預金残高 | 普通預金残高 |
| OTHER | その他 | 商品券等 |

### 3.4 取引記録機能 (F-004)

全ての金融取引を記録し、口座残高の変動を追跡する。各取引にはカテゴリを割り当て可能。

| 取引種類 | 説明 | 残高への影響 |
|----------|------|-------------|
| DEPOSIT | 入金 | ＋（増加） |
| WITHDRAWAL | 出金 | －（減少） |
| TRANSFER | 口座間振替 | ＋/－（移動） |
| BUY | 資産購入 | －（現金減少） |
| SELL | 資産売却 | ＋（現金増加） |
| DIVIDEND | 配当金受取 | ＋（増加） |
| INTEREST | 利息受取 | ＋（増加） |
| FEE | 手数料 | －（減少） |

### 3.5 API連携機能 (F-005, F-006, F-007)

外部APIと連携し、リアルタイムの価格情報を取得する。取得したデータはローカルDBにキャッシュし、オフライン時も最後に取得した価格で表示する。

| API | エンドポイント | 取得データ | 更新頻度 |
|-----|---------------|-----------|----------|
| ExchangeRate-API | api.exchangerate-api.com | 通貨間為替レート | 1日1回 |
| Alpha Vantage | www.alphavantage.co | 株価・市場データ | 営業日1回 |
| CoinGecko | api.coingecko.com | 暗号資産価格 | 15分ごと |

### 3.6 ダッシュボード機能 (F-008, F-009, F-010)

ユーザーの全資産をひと目で把握できるダッシュボード画面を提供する。

- 総資産額の表示（基準通貨換算）
- 前日比の損益表示（金額・パーセント）
- 資産配分の円グラフ（口座種類別・通貨別）
- 資産推移の折れ線グラフ（日/週/月/年）
- Pull-to-Refreshによるデータ更新

---

## 4. 非機能要件

### 4.1 セキュリティ要件

| 要件ID | 要件 | 実装方法 |
|--------|------|----------|
| NF-001 | アプリ起動時の認証必須 | BiometricPrompt + PIN/パスワード |
| NF-002 | バックグラウンド復帰時の再認証 | Lifecycle監視による自動ロック |
| NF-003 | データベースの暗号化 | SQLCipher |
| NF-004 | APIキーの安全な管理 | EncryptedSharedPreferences |
| NF-005 | 機密情報のGit除外 | local.properties + .gitignore |

### 4.2 パフォーマンス要件

| 要件ID | 要件 | 基準値 |
|--------|------|--------|
| NF-006 | アプリ起動時間 | 3秒以内（コールドスタート） |
| NF-007 | 画面遷移時間 | 1秒以内 |
| NF-008 | API応答時間 | 5秒以内（タイムアウト設定） |
| NF-009 | データベースクエリ | メインスレッドで実行しない |
| NF-010 | メモリ使用量 | 256MB以内 |

### 4.3 ユーザビリティ要件

| 要件ID | 要件 |
|--------|------|
| NF-011 | Material Design 3 ガイドラインに準拠したUI |
| NF-012 | ダーク/ライトテーマの自動・手動切替対応 |
| NF-013 | 日本語・英語の完全多言語対応 |
| NF-014 | 直感的なナビゲーション（Bottom Navigation） |
| NF-015 | 入力バリデーションとエラーメッセージの適切な表示 |

### 4.4 信頼性要件

| 要件ID | 要件 |
|--------|------|
| NF-016 | オフライン時はキャッシュデータで動作継続 |
| NF-017 | APIエラー時の適切なフォールバック処理 |
| NF-018 | データベースマイグレーション対応 |
| NF-019 | クラッシュ時のデータ保全 |

---

## 5. データベース設計

### 5.1 ER図（テーブル関連）

以下の6テーブルで構成される。テーブル間のリレーションはForeignKeyにより実装済み。

| テーブル名 | 主キー | 外部キー | リレーション |
|-----------|--------|----------|-------------|
| accounts | id (auto) | なし | 1:N → assets, transactions |
| assets | id (auto) | accountId → accounts | N:1 → accounts, 1:N → price_history |
| transactions | id (auto) | accountId → accounts, categoryId → categories | N:1 → accounts, N:1 → categories |
| categories | id (auto) | なし | 1:N → transactions |
| price_history | id (auto) | assetId → assets | N:1 → assets |
| exchange_rates | id (auto) | なし | 独立テーブル |

### 5.2 accounts テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | 口座ID |
| name | TEXT | NOT NULL | 口座名 |
| type | TEXT | NOT NULL | 口座種類（Enum → String） |
| currency | TEXT | NOT NULL | 通貨コード（JPY, USD等） |
| institution | TEXT | | 金融機関名 |
| note | TEXT | | メモ |
| createdAt | INTEGER | NOT NULL | 作成日時（Unix timestamp） |
| updatedAt | INTEGER | NOT NULL | 更新日時（Unix timestamp） |

### 5.3 assets テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | 資産ID |
| accountId | INTEGER | FK → accounts, CASCADE | 所属口座ID |
| symbol | TEXT | | シンボル（AAPL, BTC等） |
| name | TEXT | NOT NULL | 資産名 |
| type | TEXT | NOT NULL | 資産種類（Enum → String） |
| quantity | REAL | NOT NULL | 保有数量 |
| costBasis | REAL | NOT NULL | 取得原価（総額） |
| currency | TEXT | NOT NULL | 通貨コード |
| createdAt | INTEGER | NOT NULL | 作成日時 |
| updatedAt | INTEGER | NOT NULL | 更新日時 |

### 5.4 transactions テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | 取引ID |
| accountId | INTEGER | FK → accounts, CASCADE | 口座ID |
| categoryId | INTEGER | FK → categories, SET NULL | カテゴリID（null可） |
| type | TEXT | NOT NULL | 取引種類（Enum → String） |
| amount | REAL | NOT NULL | 金額 |
| currency | TEXT | NOT NULL | 通貨コード |
| date | INTEGER | NOT NULL | 取引日（Unix timestamp） |
| note | TEXT | | メモ |
| createdAt | INTEGER | NOT NULL | 作成日時 |

### 5.5 categories テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | カテゴリID |
| name | TEXT | NOT NULL | カテゴリ名 |
| icon | TEXT | | アイコン名 |
| color | TEXT | | カラーコード |
| isDefault | INTEGER | NOT NULL | デフォルトカテゴリフラグ |

### 5.6 price_history テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | 履歴ID |
| assetId | INTEGER | FK → assets, CASCADE | 資産ID |
| price | REAL | NOT NULL | 価格 |
| currency | TEXT | NOT NULL | 通貨コード |
| timestamp | INTEGER | NOT NULL, UNIQUE(assetId) | 取得日時 |

### 5.7 exchange_rates テーブル

| カラム | 型 | 制約 | 説明 |
|--------|-----|------|------|
| id | INTEGER | PRIMARY KEY, AUTO | レートID |
| fromCurrency | TEXT | NOT NULL, UNIQUE(pair) | 変換元通貨 |
| toCurrency | TEXT | NOT NULL, UNIQUE(pair) | 変換先通貨 |
| rate | REAL | NOT NULL | 為替レート |
| timestamp | INTEGER | NOT NULL | 取得日時 |

---

## 6. 画面設計

### 6.1 画面一覧

| 画面ID | 画面名 | 説明 | 対応Phase |
|--------|--------|------|-----------|
| S-001 | 認証画面 | 生体認証 / PIN入力 | Phase 3 |
| S-002 | ダッシュボード | 総資産・グラフ・サマリー | Phase 8 |
| S-003 | 口座一覧 | 登録済み口座の一覧表示 | Phase 4 |
| S-004 | 口座追加/編集 | 口座情報の入力フォーム | Phase 5 |
| S-005 | 口座詳細 | 口座の資産一覧・取引一覧 | Phase 5 |
| S-006 | 資産追加/編集 | 個別資産の入力フォーム | Phase 5 |
| S-007 | 取引一覧 | 全取引の一覧表示 | Phase 4 |
| S-008 | 取引追加/編集 | 取引情報の入力フォーム | Phase 5 |
| S-009 | 設定 | テーマ・言語・通貨・セキュリティ設定 | Phase 9 |

### 6.2 ナビゲーション構造

Bottom Navigation（4タブ構成）によるメイン画面遷移。認証画面はアプリ起動時および復帰時に表示。

| タブ | 画面 | アイコン |
|------|------|---------|
| ダッシュボード | S-002 | ic_dashboard |
| 資産 | S-003 → S-004, S-005, S-006 | ic_account_balance_wallet |
| 取引 | S-007 → S-008 | ic_receipt_long |
| 設定 | S-009 | ic_settings |

---

## 7. 技術アーキテクチャ

### 7.1 全体構成

Clean Architecture + MVVM パターンを採用。UI層 → Domain層 → Data層の3層構造により、各層の責務を明確に分離する。

| 層 | 責務 | 主要クラス |
|----|------|-----------|
| UI層 (Presentation) | 画面表示・ユーザー入力処理 | Fragment, ViewModel, Adapter |
| Domain層 | ビジネスロジック・データ変換 | UseCase, Domain Model, Repository Interface |
| Data層 | データアクセス・API通信 | Repository Impl, DAO, API Service, Entity |

### 7.2 技術スタック詳細

| 技術 | バージョン | 用途 |
|------|-----------|------|
| Room | 2.7.1 | ローカルデータベース（ORM） |
| Retrofit2 | 2.11.0 | REST API通信 |
| OkHttp | 4.12.0 | HTTPクライアント・ログ・インターセプター |
| Gson | 2.12.1 | JSON シリアライズ/デシリアライズ |
| LiveData | 2.9.0 | リアクティブなデータ監視 |
| ViewModel | 2.9.0 | UI状態管理・ライフサイクル対応 |
| Navigation | 2.9.0 | Fragment間のナビゲーション管理 |
| BiometricPrompt | 1.2.0-alpha05 | 指紋・顔認証 |
| Material Design 3 | 1.13.0 | UIコンポーネント・テーマ |

---

## 8. テスト計画

### 8.1 テスト種類

| テスト種類 | 対象 | ツール | 対応Phase |
|-----------|------|--------|-----------|
| 単体テスト | DAO, ViewModel, UseCase | JUnit + MockK | Phase 10 |
| 結合テスト | Repository + API連携 | MockWebServer | Phase 10 |
| UIテスト | 主要画面フロー | Espresso | Phase 10 |
| 実機テスト | 全機能の動作確認 | Pixel 9 (Android 15) | 全Phase |

---

## 9. 開発スケジュール

| Phase | 内容 | 状態 |
|-------|------|------|
| Phase 1 | プロジェクトセットアップ・依存関係・パッケージ構成・Git | ✅ 完了 |
| Phase 2 | データモデル・Entity・DAO・Room Database・TypeConverter | ✅ 完了 |
| Phase 3 | 生体認証・PIN認証・DB暗号化・アプリロック | 🔲 未着手 |
| Phase 4 | Bottom Navigation・Navigation Component・メイン画面構成 | 🔲 未着手 |
| Phase 5 | 口座・資産・取引のCRUD操作・バリデーション・検索 | 🔲 未着手 |
| Phase 6 | Retrofit2セットアップ・為替レートAPI連携・多通貨換算 | 🔲 未着手 |
| Phase 7 | 株価API (Alpha Vantage)・暗号資産API (CoinGecko)・WorkManager | 🔲 未着手 |
| Phase 8 | ダッシュボード・MPAndroidChart・円グラフ・折れ線グラフ | 🔲 未着手 |
| Phase 9 | strings.xml多言語化・ローカライズ・設定画面 | 🔲 未着手 |
| Phase 10 | 単体テスト・UIテスト・パフォーマンス最適化・リリースビルド | 🔲 未着手 |

---

## 10. 用語集

| 用語 | 説明 |
|------|------|
| Room | AndroidのSQLiteラッパーライブラリ（ORM） |
| DAO | Data Access Object — データベースアクセスインターフェース |
| Entity | データベーステーブルに対応するデータクラス |
| Retrofit | REST APIクライアントライブラリ |
| MVVM | Model-View-ViewModel アーキテクチャパターン |
| Clean Architecture | 関心の分離を重視する設計原則 |
| LiveData | ライフサイクル対応のオブザーバブルデータクラス |
| BiometricPrompt | Android標準の生体認証API |
| SQLCipher | SQLiteデータベースの暗号化ライブラリ |
| TypeConverter | Room用のデータ型変換クラス |

---

## 改訂履歴

| バージョン | 日付 | 変更内容 | 作成者 |
|-----------|------|----------|--------|
| 1.0 | 2026/02/15 | 初版作成 | Eliza |
