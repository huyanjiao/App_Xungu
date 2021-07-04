package android.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.mysql.jdbc.log.Log;

import android.bean.Item;
import android.db.ConnDB;

public class ItemDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	ArrayList<Item> mItemData=new ArrayList<Item>();
	public ArrayList<Item> ShowItem(Item item) {
		
		try {
			conn = ConnDB.openConn();
			String sql = "select * from item where kind=?";
			ps = conn.prepareStatement(sql);			
			ps.setString(1, item.getkind());
			rs = ps.executeQuery();	
			 
			String title,dynasty,kind,info,img;
			while(rs.next()){
				title=rs.getString(1);
				dynasty=rs.getString(2);//
				kind=rs.getString(3);//
				info=rs.getString(4);//
				img=rs.getString(5);//)
				mItemData.add(new Item(title,dynasty,kind,info,img));
				
          }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return mItemData;
	}
	
	public int InsertItem(Item item) {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql ="insert into item(title,dynasty,kind,info,img) values(?,?,?,?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, item.gettitle());
			ps.setString(2, item.getdynasty());
			ps.setString(3, item.getkind());
			ps.setString(4, item.getinfo());
			ps.setString(5, item.getimg());
			
			num = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}

	public int search() {
		int num = 0;
		try {
			conn = ConnDB.openConn();
			String sql ="select count(*) as result from item ;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();			 
            while(rs.next()){
               num=rs.getInt(1);
           }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnDB.closeConn(rs, ps, conn);
		}
		return num;
	}
	

}
