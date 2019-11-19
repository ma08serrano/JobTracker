package ca.senecacollege.JobTracker.DatabaseHelper;

/*
 * This is jobs class used by MyDBHandler class to process job json object that is fetched out from the
 * git hub json. it takes following parameters: title, description, organization, org_location, org_email
 * post_origin, post_url, post_deadline, applied_date, interview_date, offer_deadline, note, status_id
 * and org_addr_id which is a foreign key relationship to the address table: org_addr_id column
 *
 */

public class Jobs{
        private int job_id;
        private String title;
        private String description;
        private String organization;
        private String org_location;
        private String org_email;
        private String post_origin;
        private String post_url;
        private String post_deadline;
        private String applied_date;
        private String interview_date;
        private String offer_deadline;
        private String note;
        private int org_addr_id;
        private int status_id;

        public Jobs(){}

        public Jobs(String _title, String _description, String _organization, String _org_location, String _org_email, String _post_origin, String _post_url, String _post_deadline, String _applied_date, String _interview_date, String _offer_deadline, String _note, int _org_addr_id, int _status_id){
            this.title = _title;
            this.description = _description;
            this.organization  = _organization;
            this.org_location  = _org_location;
            this.org_email = _org_email;
            this.post_origin = _post_origin;
            this.post_url  = _post_url ;
            this.post_deadline = _post_deadline;
            this.applied_date = _applied_date;
            this.interview_date = _interview_date;
            this.offer_deadline = _offer_deadline;
            this.note  = _note;
            this.org_addr_id = _org_addr_id;
            this.status_id = _status_id;
        }

        public void setJobId(int _job_id){
            this.job_id  = _job_id;
        }

        public int getJobId() {
            return this.job_id;
        }

        public void setTitle(String _title){
            this.title = _title;
        }

        public String getTitle(){
            return this.title;
        }

        public void setDescription(String _description){
            this.description = _description;
        }

        public String getDescription() {
            return this.description;
        }

        public void setOrganization(String _organization){
            this.organization = _organization;
        }

        public String getOrganization() {
            return this.organization;
        }

        public void setOrgLocation(String _org_location){
            this.org_location = _org_location;
        }

        public String getOrg_location() {
            return this.org_location;
        }

        public void setOrgEmail(String _org_email){
            this.org_email = _org_email;
        }

        public String getOrg_email() {
            return this.org_email;
        }

        public void setPostOrigin(String _post_origin){
            this.post_origin = _post_origin;
        }

        public String getPost_origin () {
            return this.post_origin;
        }

        public void setPostUrl(String _post_url){
            this.post_url = _post_url;
        }

        public String getPost_url () {
            return this.post_url;
        }

        public void setPostDeadline(String _post_deadline){
            this.post_deadline = _post_deadline;
        }

        public String getPost_deadline () {
            return this.post_deadline;
        }

        public void setAppliedDate(String _applied_date){
            this.applied_date = _applied_date;
        }

        public String getApplied_date() {
            return this.applied_date;
        }

        public void setInterviewDate(String _interview_date){
            this.interview_date = _interview_date;
        }

        public String getInterview_date() {
            return this.interview_date;
        }

        public void setOfferDeadline(String _offer_deadline){
            this.offer_deadline = _offer_deadline;
        }

        public String getOffer_deadline() {
            return this.offer_deadline;
        }

        public void setNote(String _note){
            this.note = _note;
        }

        public String getNote() {
            return this.note;
        }

        public void setOrgAddrId(int _org_addr_id){
            this.org_addr_id = _org_addr_id;
        }

        public int getOrg_addr_id() {
            return this.org_addr_id;
        }

        public void setStatusId(int _status_id){
            this.status_id = _status_id;
        }

        public int getStatus_id() {
            return this.status_id;
        }

}
