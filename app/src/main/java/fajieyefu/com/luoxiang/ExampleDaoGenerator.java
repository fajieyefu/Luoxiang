package fajieyefu.com.luoxiang;

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
