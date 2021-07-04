package android.bean;

public class Item {
	private String title;
	private String dynasty;
	private String kind;
	private String info;
	private String img;
	public Item(String title, String dynasty, String kind,String info,String img) {
        this.title = title;
        this.dynasty=dynasty;
        this.kind = kind;
        this.info = info;
        this.img = img;
    }

	public Item() {
		// TODO Auto-generated constructor stub
	}

	public String gettitle() {
		return title;
	}
	public void settitle(String title) {
		this.title = title;
	}
	public String getdynasty() {
		return dynasty;
	}

	public void setdynasty(String dynasty) {
		this.dynasty = dynasty;
	}
	public String getkind() {
		return kind;
	}

	public void setkind(String kind) {
		this.kind = kind;
	}


	public String getinfo() {
		return info;
	}
	
	public void setinfo(String info) {
		this.info = info;
	}

	public String getimg() {
		return img;
	}

	public void setimg(String img) {
		this.img = img;
	}


}
