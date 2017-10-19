package fajieyefu.com.luoxiang;

import java.util.Map;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * Created by qiancheng on 2017/3/24.
 */

public class ExampleDaoGenerator {
	public static String OUT_DIR = "G:\\Yundiandao\\luoxiang\\luoxiang\\app\\src\\main\\java-gen";
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
		inventoryClass.addStringProperty("standardId");//标准编号
		inventoryClass.addStringProperty("standardMoney");//标准价格
		//物料编码表
		Entity inventory = schema.addEntity("Inventory");
		inventory.addIdProperty();
		inventory.addStringProperty("cInvCCode");//存货分类编码
		inventory.addStringProperty("cInvCode");//存货编码
		inventory.addStringProperty("cInvName");//存货名称
		inventory.addStringProperty("deMoney");//差价
		inventory.addStringProperty("realMoney");//实际价
		inventory.addStringProperty("counts");//数量
		inventory.addFloatProperty("weight");//重量
		inventory.addStringProperty("classId");//物料编号
		inventory.addStringProperty("standardId");//所属标准编号
		inventory.addIntProperty("isCurrent");//默认选择项 1 默认选择，0非默认选择
		inventory.addStringProperty("cInvStd");//产品型号
		inventory.addStringProperty("u8code");//U8中的编码，不需要处理
		inventory.addIntProperty("styleflag");//所属bom表示
		inventory.addIntProperty("iSupplyType");//供应类型
		inventory.addStringProperty("cInvDefine1");//u8中自定义项站柱和挡板匹配
		//记录审批的次数,作废
		Entity auditCount = schema.addEntity("AuditCount") ;
		auditCount.addIdProperty();
		auditCount.addIntProperty("counts");
		auditCount.addStringProperty("username");
		//记录上一次审批时间
		Entity lastCheckInfo = schema.addEntity("LastCheckInfo") ;
		lastCheckInfo.addIdProperty();
		lastCheckInfo.addLongProperty("lastCheckTime");
		lastCheckInfo.addStringProperty("username");


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
