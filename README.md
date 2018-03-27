# Tree Source
> 数据源树形展示平台

## etl 支持
1 etl 中间结果全部进入消息队列中
2 mysql -> hbase -> redis

scan 条件 -> producer -> mq -> consumer -> hbase(格式)
producer 完成通知 consumer 没有消息可以执行退出。然后启动第二套流程
该流程的所有配置都保存到了，xml
其中  mq 可以是，jvm blockque