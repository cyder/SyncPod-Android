# YouTubeSync
YouTubeSyncのAndroidアプリ用のリポジトリです。

## 必要環境
* Android Studio 2.3.3
* Android SDK

### Android Studio 2.3.3
[ここ](https://developer.android.com/studio/archive.html)からインストールする。

### Android SDK
Android Studio内のSDK Managerから、デバッグ用実機端末のバージョンに合ったSDKをダウンロードする。

## セットアップ
1. 次のコマンドを実行する。
```sh
git@github.com:Mori-Atsushi/YouTubeSync.git
cd YouTubeSync
```

2. YouTube Data APIのAPIキーをセットする\
[Google Cloud Console](https://console.developers.google.com/)にアクセスして、YouTube Data API v3用のAPIキーを取得し、以下のコマンドを実行する。
```
launchctl setenv YOUTUBE_DEVELOPER_KEY <APIキー>
```

3. Android Studioでプロジェクトを開く

4. デバッグ用実機端末を接続し、Runから起動させる。

## 著者
* [森 篤史](@Mori-Atsushi)
