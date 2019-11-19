package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 *
 * This is Address class used by MyDBHandler class to structure getter / setter for following parameters:
 * address_id, street_no, street_name, city, province_state, postal_zip_code, country and org_addr_id.
 * Note that org_addr_id is a primary key relationship to Jobs class
 *
 */

public class Address{
        private int address_id;
        private String street_no;
        private String street_name;
        private String city;
        private String province_state;
        private String postal_zip_code;
        private String country;
        private String org_addr_id;

        public Address(){}

        public Address(int _address_id, String _street_no, String _street_name, String _city, String _province_state, String _postal_zip_code, String _country, String _org_addr_id){
            this.address_id = _address_id;
            this.street_no = _street_no;
            this.street_name = _street_name;
            this.city = _city;
            this.province_state = _province_state;
            this.postal_zip_code = _postal_zip_code;
            this.country = _country;
            this.org_addr_id = _org_addr_id;
        }

        public void setAddressId(int _address_id){
            this.address_id = _address_id;
        }

        public int getAddress_id() {
            return this.address_id;
        }

        public void setStreetNo(String _street_no){
            this.street_no = _street_no;
        }

        public String getStreet_no() {
            return this.street_no;
        }

        public void setStreetName(String _street_name){
            this.street_name = _street_name;
        }

        public String getStreet_name() {
            return this.street_name;
        }

        public void setCity(String _city){
            this.city = _city;
        }

        public String getCity() {
            return this.city;
        }

        public void setProvinceState(String  _province_state){
            this.province_state = _province_state;
        }

        public String getProvince_state() {
            return this.province_state;
        }

        public void setZipCode(String _postal_zip_code){
            this.postal_zip_code = _postal_zip_code;
        }

        public String getPostal_zip_code() {
            return this.postal_zip_code;
        }

        public void setCountry(String  _country){
            this.country = _country;
        }


        public String getCountry() {
            return this.country;
        }

        public void setOrgAddrId(String _org_addr_id){
            this.org_addr_id = _org_addr_id;
        }

        public String getOrg_addr_id() {
            return this.org_addr_id;
        }

}
