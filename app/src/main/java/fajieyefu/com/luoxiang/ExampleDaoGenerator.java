package fajieyefu.com.luoxiang;

import java.util.Map;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by qiancheng on 2017/3/24.
 */

public class ExampleDaoGenerator {
	public static String OUT_DIR = "G:\\Yundiandao\\Luoxiang2\\app\\src\\main\\java-gen";
	private static void addTaskDetails(Schema schema){
		//用户信息类
		Entity entity=schema.addEntity("UserInfo");
		entity.addIdProperty();//添加ID属性
		entity.addStringProperty("username");
		entity.addStringProperty("password");
		entity.addBooleanProperty("rembPsw");
		//物料分类表
		Entity inventoryClass = schema.addEntity("InventoryClass");
		inventoryClass.addIdProperty();
		inventoryClass.addStringProperty("cInvCCode");//存货分类编码
		inventoryClass.addStringProperty("cInvCName");//存货分类名称
		inventoryClass.addStringProperty("standardId");//标准
		//物料编码表
		Entity inventory = schema.addEntity("Inventory");
		inventory.addIdProperty();
		inventory.addStringProperty("cInvCode");//存货编码
		inventory.addStringProperty("cInvName");//存货名称
		inventory.addStringProperty("deMoney");//差价
		inventory.addStringProperty("realMoney");//标准价
		inventory.addStringProperty("counts");//数量
		inventory.addStringProperty("weight");//重量

	}
	public static void main(String[] args)throws Exception{
		//生成数据库文件的目标包名
		//第一个参数是数据库版本号，第二个参数是包的根目录的包
		Schema schema = new Schema(1,"fajieyefu.com.luoxiang");
		addTaskDetails(schema);
		try{
			new DaoGenerator().generateAll(schema,OUT_DIR);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
