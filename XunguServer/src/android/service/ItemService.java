package android.service;

import java.util.ArrayList;

import android.bean.Item;
import android.dao.ItemDAO;

public class ItemService {
   ItemDAO itemDAO = new ItemDAO();
	
	public  ArrayList<Item> ShowItem(Item item){
		return itemDAO.ShowItem(item);
	}
	
	public int InsertItem(Item item){
		return itemDAO.InsertItem(item);
	}
	public int search(){
		return itemDAO.search();
	}


}
