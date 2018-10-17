# Toshokan

色々な技術を盛り込んでアプリを作りました。  
  
プログラミングには`kotlin`を使用しています。  
`CoordinatorLayout`を使用してヘッダーが画面外に吸い込まれるレイアウトを実装しています。  
また`ConstraintLayout`を使用して文字や画像を配置しています。  
それぞれの画面は`Fragment`です。  

使用している外部ライブラリは  
APIアクセスに`Retrofit` https://square.github.io/retrofit/  
Responseの解析に`gson` https://github.com/google/gson  
画像取得に`Glide` https://github.com/bumptech/glide  
HTTP通信には`OkHttp` http://square.github.io/okhttp/  
イベント処理には`Rx(RxAndroid、RxKotlin、RxJava)` http://reactivex.io/ を使用しています。  

画像はFlickr API https://www.flickr.com/services/api/  
人口データはResas API https://opendata.resas-portal.go.jp/  
を使用しています。  
