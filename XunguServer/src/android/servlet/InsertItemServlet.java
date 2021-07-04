package android.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import android.bean.Item;
import android.service.ItemService;



public class InsertItemServlet extends HttpServlet {
    public String img;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		BufferedReader reader;
        StringBuffer sb = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        String lines;
        while ((lines = reader.readLine()) != null) {
            sb.append(lines);
        }
        String result = sb.toString();
		String title = null,dynasty = null,kind = null,info = null;
		ItemService itemService = new ItemService();
		int nums = itemService.search();	
		String img = String.valueOf(nums);
	    String[] it = result.split(",");
	    title = it[0];
	    dynasty = it[1];
	    kind = it[2];
	    info = it[3];
	    	
		Item item  = new Item();
		item.settitle(title);
		item.setdynasty(dynasty);
		item.setkind(kind);
		item.setinfo(info);
		item.setimg(img);				
		int num = itemService.InsertItem(item);	
		response.getWriter().write(num + "");
		
	}
	
}
