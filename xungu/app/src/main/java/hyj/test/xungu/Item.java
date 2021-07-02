/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hyj.test.xungu;

import android.graphics.Bitmap;

/**
 * Data model for each row of the RecyclerView
 */
class Item {
    private String title;
    private String dynasty;
    private String kind;
    private String info;
    private String img;
    private Bitmap bmp;
    Item(String title, String dynasty, String kind,String info,String img) {
        this.title = title;
        this.dynasty=dynasty;
        this.kind = kind;
        this.info = info;
        this.img = img;
    }
    public void setbmp(Bitmap bmp) {
        this.bmp = bmp;
    }
    public Bitmap getbmp() {
        return bmp;
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

