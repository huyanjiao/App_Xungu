package android.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import android.bean.Item;
import android.service.ItemService;



public class SaveimgServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		InputStream is = request.getInputStream();
        DataInputStream dis = new DataInputStream(is);

        String result = "";
        try {
            result = saveFile(dis);
        } catch (Exception e) {
            e.printStackTrace();
            result = "uploaderror";
        }

        request.getSession().invalidate();
        response.setContentType("image/jpeg;charset=UTF-8");
        ObjectOutputStream dos = new ObjectOutputStream(
                    response.getOutputStream());
        dos.writeObject(result);
        dos.flush();
        dos.close();
        dis.close();
        is.close();
		
	}
	private String saveFile(DataInputStream dis) {
		ItemService itemService = new ItemService();
		int num = itemService.search();	
		String img = String.valueOf(num);
		
	    String fileurl = "C:/Program Files/Java/tomcat10/webapps/ForAndroid/img/"+img+".jpg";
        File file = new File(fileurl);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream fps = null;
        try {
            fps = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int length = -1;

        try {
            while ((length = dis.read(buffer)) != -1) {
                fps.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fps.flush();
            fps.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


}
