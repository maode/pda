# 打印机数据采集服务

pda -> Printer Data Acquisition

该项目为 打印机数据采集服务 程序。

# 使用说明

## 打包

在项目根目录 运行 `mvn clean package assembly:single -DskipTests=true` 进行打包。或运行 `package.bat`批处理文件自动打包。

打包完成后会在`target`目录下生成一个`with-dependencies`后缀的`jar`文件。

## 运行

该程序运行时需传递一个“端口”参数。如：`java -jar pda-1.0-jar-with-dependencies.jar 6327`

## 保存日志文件
运行时要在当前jar包所在路径下执行命令，否则不会保存日志文件。（或者也可以修改log4j2.xml的配置，将日志路径配置为绝对路径） 
