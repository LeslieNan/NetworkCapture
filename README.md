# NetworkCapture
网络抓包数据
针对单个APP进行网络数据的收集，并提供检索，编辑等功能
## 集成方式：
> implementation 'io.github.leslienan:NetworkCapture:0.0.6-alpha'

在项目使用到的Retrofit配置中添加拦截器
> .addInterceptor(WiresharkInterceptor())

