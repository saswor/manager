//package test;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.commons.io.IOUtils;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.manage.ManageApplication;
////import com.manage.project.tool.gen.service.IGenService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes={ManageApplication.class})// 指定启动类
//@MapperScan("com.manage.project.*.*.mapper")
//public class GenTest {
//	
//	@Autowired
////    private IGenService genService;
//
//	@Test
//    public void gen() throws IOException{
////		List<String> tables = new ArrayList<String>();
////		tables.add("as_report_board");tables.stream().st
////		String[] a = (String[]) tables.toArray();
////		byte[] data = genService.generatorCode(a);
//		
//		
//		ArrayList<String> tables=new ArrayList<String>();
//		tables.add("sys_dispatch");
////		tables.add("as_advert_config");
////		tables.add("as_advert_device");
////		tables.add("as_advert_material");
////		tables.add("as_advert_mstrategy");
////		tables.add("as_advert_strategy");
////		tables.add("as_corp");
////		tables.add("as_emp");
////		tables.add("as_favourable_config");
////		tables.add("as_favourable_object");
////		tables.add("as_favourable_time");
////		tables.add("as_order_apply");
////		tables.add("as_order_box");
////		tables.add("as_order_box_detail");
////		tables.add("as_order_change");
////		tables.add("as_order_product");
////		tables.add("as_payconfig_alipay");
////		tables.add("as_payconfig_wechat");
////		tables.add("as_product_classify");
////		tables.add("as_product_info");
////		tables.add("as_product_lunder");
////		tables.add("as_product_online");
////		tables.add("as_product_under");
////		tables.add("as_report_board");
////		tables.add("as_report_dsale");
////		tables.add("as_report_msale");
////		tables.add("as_report_osale");
////		tables.add("as_statement_balance");
////		tables.add("as_statement_product");
////		tables.add("as_statement_supply");
////		tables.add("as_stock_inbound");
////		tables.add("as_stock_pinbound");
////		tables.add("as_stock_ppurchase");
////		tables.add("as_stock_product");
////		tables.add("as_stock_purchase");
////		tables.add("as_stock_warehouse");
////		tables.add("as_supply_config");
////		tables.add("as_supply_order");
////		tables.add("as_supply_product");
////		tables.add("as_supply_vending");
////		tables.add("as_supply_vproduct");
////		tables.add("as_vending");
////		tables.add("as_vending_cabinet");
////		tables.add("as_vending_cmd");
////		tables.add("as_vending_district");
////		tables.add("as_vending_event");
////		tables.add("as_vending_lane");
////		tables.add("as_vending_lanep");
////		tables.add("as_vending_line");
////		tables.add("as_vending_model");
////		tables.add("as_vending_pconfig");
////		tables.add("as_vending_person");
////		tables.add("as_vending_plane");
////		tables.add("as_vending_point");
////		tables.add("as_vending_state");
////		tables.add("as_vending_statistic");
////		tables.add("as_vending_stock");
////		tables.add("as_vending_upgrade");
////		tables.add("as_vending_upgrade_task");
////		tables.add("as_vending_warn");
////		String[] strings = new String[tables.size()];
////		tables.toArray(strings);
////		byte[] data = genService.generatorCode(strings);
////		OutputStream out = new FileOutputStream("D:\\sysdispatch.zip"); 
////		IOUtils.write(data, out);
//    }
//}
