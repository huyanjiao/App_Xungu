package android.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.bean.Item;
import android.service.ItemService;



public class ShowItemServlet extends HttpServlet {

	/**
	 * 
	 */
	

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String kind = request.getParameter("kind");
		response.setContentType("text/html;charset=UTF-8");
		Item item =new Item();
		item.setkind(kind);
		
		ItemService itemService = new ItemService();
		ArrayList<Item> itemData =new ArrayList<Item>();
		itemData = itemService.ShowItem(item);
		String out = "";

		for (Item i : itemData) {
			out = out +i.gettitle() + ","+ i.getdynasty() +","+ i.getkind() + ","+ i.getinfo()+","+i.getimg()+";";
		}
		
		PrintWriter outs = response.getWriter();
		response.setCharacterEncoding("utf-8");
		outs.write(out);
		outs.flush();
		
		
	}

}