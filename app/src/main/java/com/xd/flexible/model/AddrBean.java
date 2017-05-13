package com.xd.flexible.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Flexible on 2017/4/6 0006.
 */

public class AddrBean extends BaseBean {

    public List<AddrlistBean> addrlist;

    public static class AddrlistBean  implements Serializable{
        /**
         * addr_id : 1
         * user_id : 2
         * name : 111
         * phone : 222
         * province : 山东省
         * city : 烟台
         * district : 莱山
         * addr : xxxx
         * is_default : 1
         * create_time : 2017-04-06 14:02:44
         */

        public int addr_id;
        public int user_id;
        public String name;
        public String phone;
        public String province;
        public String city;
        public String district;
        public String addr;
        public int is_default;
        public String create_time;
    }
}
