package ma.uca.pacta.utils;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import com.google.protobuf.ServiceException;

public class ZookeeperConnection {
	
	public static Configuration config;

	static {
		System.setProperty("hadoop.home.dir", "/usr/local/hadoop/hadoop-2.7.7");		
		config = HBaseConfiguration.create();
		String path = ZookeeperConnection.class.getClassLoader().getResource("hbase-site.xml").getPath();		
		config.addResource(new Path(path));				
		try {
			HBaseAdmin.checkHBaseAvailable(config);
		} catch (MasterNotRunningException e) {
			System.out.println("MasterNotRunningException error : " + e.getMessage());
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			System.out.println("ZooKeeperConnectionException error : " + e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			System.out.println("ServiceException error : " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException erro : " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Connection success to Zookeeper.");
	}
}
