package com.mcode.transferservice.pojo;

public class TransactionPojo {
    private String status;
    private String message;
    private Data DataObject;


    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return DataObject;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data dataObject) {
        this.DataObject = dataObject;
    }
}
class Data {
    private float id;
    private String account_number;
    private String bank_code;
    private String full_name;
    private String created_at;
    private String currency;
    private String debit_currency;
    private float amount;
    private float fee;
    private String status;
    private String reference;
    private String meta = null;
    private String narration;
    private String approver = null;
    private String complete_message;
    private float requires_approval;
    private float is_approved;
    private String bank_name;


    // Getter Methods

    public float getId() {
        return id;
    }

    public String getAccount_number() {
        return account_number;
    }

    public String getBank_code() {
        return bank_code;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDebit_currency() {
        return debit_currency;
    }

    public float getAmount() {
        return amount;
    }

    public float getFee() {
        return fee;
    }

    public String getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public String getMeta() {
        return meta;
    }

    public String getNarration() {
        return narration;
    }

    public String getApprover() {
        return approver;
    }

    public String getComplete_message() {
        return complete_message;
    }

    public float getRequires_approval() {
        return requires_approval;
    }

    public float getIs_approved() {
        return is_approved;
    }

    public String getBank_name() {
        return bank_name;
    }

    // Setter Methods

    public void setId(float id) {
        this.id = id;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setDebit_currency(String debit_currency) {
        this.debit_currency = debit_currency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public void setComplete_message(String complete_message) {
        this.complete_message = complete_message;
    }

    public void setRequires_approval(float requires_approval) {
        this.requires_approval = requires_approval;
    }

    public void setIs_approved(float is_approved) {
        this.is_approved = is_approved;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }
}
