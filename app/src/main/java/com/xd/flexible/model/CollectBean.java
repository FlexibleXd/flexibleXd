package com.xd.flexible.model;

import java.util.List;

/**
 * Created by Flexible on 2017/4/7 0007.
 */

public class CollectBean  extends BaseBean{

    /**
     * code : 1
     * msg : 操作成功
     * collections : [{"collect_id":1,"user_id":2,"pro_id":1,"create_time":"2017-04-07 08:56:01","pro":{"pro_id":1,"class_id":1,"pro_name":"清扬","pro_img":"","price":18,"member_price":15.98,"supplier":"A公司","product_type":"热门产品","create_time":"2017-04-01 15:03:00","update_time":"2017-04-01 15:08:27"}}]
     * pager : {"pageNumber":1,"pageSize":10,"pageCount":0,"recordCount":0}
     */

    public PagerBean pager;
    public List<CollectionsBean> collections;



    public static class CollectionsBean {
        /**
         * collect_id : 1
         * user_id : 2
         * pro_id : 1
         * create_time : 2017-04-07 08:56:01
         * pro : {"pro_id":1,"class_id":1,"pro_name":"清扬","pro_img":"","price":18,"member_price":15.98,"supplier":"A公司","product_type":"热门产品","create_time":"2017-04-01 15:03:00","update_time":"2017-04-01 15:08:27"}
         */

        public int collect_id;
        public int user_id;
        public int pro_id;
        public String create_time;
        public ProductBean pro;

    }
}
