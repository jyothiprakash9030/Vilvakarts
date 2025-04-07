package com.example.vcartbusbooking.utils;

import java.util.ArrayList;

public class NotPackedOrdersResponse {
    private int statuscode;
    private boolean success;
    private String message;
    private int total;
    private ArrayList<Data> data;

    // Getters and Setters
    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public static class Data {
        private int id;
        private int storeid;
        private String order_id;
        private int order_status;
        private int user_id;
        private String name;
        private String mobile_prefix;
        private String mobile;
        private String currency;
        private int is_wallet;
        private int amt;
        private int totalamt;
        private int wallet_used;
        private int deliverychg;
        private int cod_charge;
        private String payment_status;
        private String payment_date;
        private Object address_id;
        private String deliveryaddress;
        private int country;
        private Object testing_column;
        private String cartinfodata;
        private Object inv_number;
        private String product_ids;
        private String referral;
        private String shipping_method;
        private String payment_method;
        private String payment_id;
        private Object payment_intent_id;
        private String ref_no;
        private String note;
        private Object coupon_code;
        private Object cashback_id;
        private int discount;
        private Object migrated;
        private String record_date;
        private String created_at;
        private String updated_at;
        private Object tracking_awb;
        private Object tracking_courier;
        private Object shipped_at;
        private Object delivered_at;
        private int updated_via;

        // Getters and Setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStoreid() {
            return storeid;
        }

        public void setStoreid(int storeid) {
            this.storeid = storeid;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile_prefix() {
            return mobile_prefix;
        }

        public void setMobile_prefix(String mobile_prefix) {
            this.mobile_prefix = mobile_prefix;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public int getIs_wallet() {
            return is_wallet;
        }

        public void setIs_wallet(int is_wallet) {
            this.is_wallet = is_wallet;
        }

        public int getAmt() {
            return amt;
        }

        public void setAmt(int amt) {
            this.amt = amt;
        }

        public int getTotalamt() {
            return totalamt;
        }

        public void setTotalamt(int totalamt) {
            this.totalamt = totalamt;
        }

        public int getWallet_used() {
            return wallet_used;
        }

        public void setWallet_used(int wallet_used) {
            this.wallet_used = wallet_used;
        }

        public int getDeliverychg() {
            return deliverychg;
        }

        public void setDeliverychg(int deliverychg) {
            this.deliverychg = deliverychg;
        }

        public int getCod_charge() {
            return cod_charge;
        }

        public void setCod_charge(int cod_charge) {
            this.cod_charge = cod_charge;
        }

        public String getPayment_status() {
            return payment_status;
        }

        public void setPayment_status(String payment_status) {
            this.payment_status = payment_status;
        }

        public String getPayment_date() {
            return payment_date;
        }

        public void setPayment_date(String payment_date) {
            this.payment_date = payment_date;
        }

        public Object getAddress_id() {
            return address_id;
        }

        public void setAddress_id(Object address_id) {
            this.address_id = address_id;
        }

        public String getDeliveryaddress() {
            return deliveryaddress;
        }

        public void setDeliveryaddress(String deliveryaddress) {
            this.deliveryaddress = deliveryaddress;
        }

        public int getCountry() {
            return country;
        }

        public void setCountry(int country) {
            this.country = country;
        }

        public Object getTesting_column() {
            return testing_column;
        }

        public void setTesting_column(Object testing_column) {
            this.testing_column = testing_column;
        }

        public String getCartinfodata() {
            return cartinfodata;
        }

        public void setCartinfodata(String cartinfodata) {
            this.cartinfodata = cartinfodata;
        }

        public Object getInv_number() {
            return inv_number;
        }

        public void setInv_number(Object inv_number) {
            this.inv_number = inv_number;
        }

        public String getProduct_ids() {
            return product_ids;
        }

        public void setProduct_ids(String product_ids) {
            this.product_ids = product_ids;
        }

        public String getReferral() {
            return referral;
        }

        public void setReferral(String referral) {
            this.referral = referral;
        }

        public String getShipping_method() {
            return shipping_method;
        }

        public void setShipping_method(String shipping_method) {
            this.shipping_method = shipping_method;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(String payment_id) {
            this.payment_id = payment_id;
        }

        public Object getPayment_intent_id() {
            return payment_intent_id;
        }

        public void setPayment_intent_id(Object payment_intent_id) {
            this.payment_intent_id = payment_intent_id;
        }

        public String getRef_no() {
            return ref_no;
        }

        public void setRef_no(String ref_no) {
            this.ref_no = ref_no;
        }

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public Object getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(Object coupon_code) {
            this.coupon_code = coupon_code;
        }

        public Object getCashback_id() {
            return cashback_id;
        }

        public void setCashback_id(Object cashback_id) {
            this.cashback_id = cashback_id;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

        public Object getMigrated() {
            return migrated;
        }

        public void setMigrated(Object migrated) {
            this.migrated = migrated;
        }

        public String getRecord_date() {
            return record_date;
        }

        public void setRecord_date(String record_date) {
            this.record_date = record_date;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public Object getTracking_awb() {
            return tracking_awb;
        }

        public void setTracking_awb(Object tracking_awb) {
            this.tracking_awb = tracking_awb;
        }

        public Object getTracking_courier() {
            return tracking_courier;
        }

        public void setTracking_courier(Object tracking_courier) {
            this.tracking_courier = tracking_courier;
        }

        public Object getShipped_at() {
            return shipped_at;
        }

        public void setShipped_at(Object shipped_at) {
            this.shipped_at = shipped_at;
        }

        public Object getDelivered_at() {
            return delivered_at;
        }

        public void setDelivered_at(Object delivered_at) {
            this.delivered_at = delivered_at;
        }

        public int getUpdated_via() {
            return updated_via;
        }

        public void setUpdated_via(int updated_via) {
            this.updated_via = updated_via;
        }
    }
}

