package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/26.
 */

public class UpdateBean implements Serializable {

    /**
     * code : 0
     * message : success
     * data : {"niu_index_response":{"constraint":1,"new_version":"3.10","apk_file_url":"http://app.zhongyaojixie.com/apk.php","update_log":"优化用户体验度","target_size":"10m"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * niu_index_response : {"constraint":1,"new_version":"3.10","apk_file_url":"http://app.zhongyaojixie.com/apk.php","update_log":"优化用户体验度","target_size":"10m"}
         */

        private NiuIndexResponseBean niu_index_response;

        public NiuIndexResponseBean getNiu_index_response() {
            return niu_index_response;
        }

        public void setNiu_index_response(NiuIndexResponseBean niu_index_response) {
            this.niu_index_response = niu_index_response;
        }

        public static class NiuIndexResponseBean {
            /**
             * constraint : 1
             * new_version : 3.10
             * apk_file_url : http://app.zhongyaojixie.com/apk.php
             * update_log : 优化用户体验度
             * target_size : 10m
             */

            private int constraint;
            private String new_version;
            private String apk_file_url;
            private String update_log;
            private String target_size;

            public int getConstraint() {
                return constraint;
            }

            public void setConstraint(int constraint) {
                this.constraint = constraint;
            }

            public String getNew_version() {
                return new_version;
            }

            public void setNew_version(String new_version) {
                this.new_version = new_version;
            }

            public String getApk_file_url() {
                return apk_file_url;
            }

            public void setApk_file_url(String apk_file_url) {
                this.apk_file_url = apk_file_url;
            }

            public String getUpdate_log() {
                return update_log;
            }

            public void setUpdate_log(String update_log) {
                this.update_log = update_log;
            }

            public String getTarget_size() {
                return target_size;
            }

            public void setTarget_size(String target_size) {
                this.target_size = target_size;
            }
        }
    }
}
