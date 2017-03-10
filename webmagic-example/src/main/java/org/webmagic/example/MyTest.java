package org.webmagic.example;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 抓取台风列表
 * 
 * @author Think
 *
 */
public class MyTest implements PageProcessor {
	// 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(10000);

	public static void main(String[] args) {
		Spider.create(new MyTest()).addUrl("http://typhoon.weather.com.cn/hist")
				// 保存成文件
				.addPipeline(new JsonFilePipeline("D:\\webmagic\\"))
				// 开启5个线程抓取
				.thread(5)
				// 启动爬虫
				.run();
		// ResultItems resultItemses =
		// spider.get("http://typhoon.weather.com.cn/his");
		// System.out.println(resultItemses);
	}

	@Override
	public void process(Page page) {
		page.addTargetRequests(page.getHtml().links().regex("(http://typhoon.weather.com.cn/hist/[\\w]+.shtml)").all());
		page.putField("title", page.getHtml().xpath("/html/body/div[4]/div[2]/div[1]/div/dl/dd/a/text()").all());/// dl/dd/a/text()"));
		page.putField("url", page.getHtml().xpath("/html/body/div[4]/div[2]/div[1]/div/dl/dd/a/@href").all());

		// if (page.getResultItems().get("title") == null) {
		// skip this page
		// page.setSkip(true);
		// }
	}

	@Override
	public Site getSite() {
		return site;
	}
}
