package hyj.test.xungu;

/**
 * Data model for each row of the RecyclerView
 */
class Guide {
    private String title;
    private String info;
    private int img;

    Guide(String title, String info,int img) {
        this.title = title;

        this.info = info;
        this.img = img;
    }

    public String gettitle() {
        return title;
    }
    public void settitle(String title) {
        this.title = title;
    }



    public String getinfo() {
        return info;
    }

    public void setinfo(String info) {
        this.info = info;
    }

    public int getimg() {
        return img;
    }

    public void setimg(int img) {
        this.img = img;
    }


}

