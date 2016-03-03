# ChineseHousePriceCrawler
A crawler collect house price from lianjia.com

使用webmagic开发的一个简单的爬虫，爬取lianjia网的数据并存取到mysql中。

使用需要您自己创建一个mysql并建2张表，一张存储房屋链接，一张存储房屋具体信息。项目中可以找到建表脚本。

使用步骤：

1、CrawlerImpl中设置pageLink，把pageLink修改成目标房源。

2、CrawlerImpl中注释掉crawler.getHouseDetail()，执行crawler.getHouseLink()，等待执行完毕。

3、注释crawler.getHouseLink()执行crawler.getHouseDetail()。执行完毕后，房源信息就存在您的mysql库中了。



----------------------------mysql 建表脚本--------------------------------------

create table house(
	id int(20) auto_increment not null,
	link varchar(128) not null,
	isCrawled boolean not null,
	primary key (id)
);

create table house_detail(
	id int(20) auto_increment not null,
	add_time timestamp(6) not null,
	link varchar(128) not null,
	title varchar(128) not null,
	price_per_square int(32) not null,
	total_price int(16) not null,
	total_area int(32) not null,
	house_type varchar(32) not null,
	house_estate varchar(16) not null,
	built_year int(16) not null,
	house_towards varchar(32) not null,
	house_floor varchar(32) not null,
	seller varchar(32) not null,
	seller_phone_number varchar(16),
	primary key (id)
);

1.房源名称
2.房子每平米价格
3.房子总价格
4.房子总面积
5.房子户型
6.房子小区
7.房子建造年代
8.房子朝向
9.房子楼层
10.经纪人
11.经纪人电话