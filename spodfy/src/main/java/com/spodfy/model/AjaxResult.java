package com.spodfy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author milesmorales
 * Body padrao para as requisicoes
 */
public class AjaxResult implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2003754118391931015L;
    public enum Status {

        ERRO(1), SUCESSO(2);

        private int cod;

        private Status(int cod) {
            this.cod = cod;
        }

        public int getCod() {
            return cod;
        }
    }

    /**
     * Status da requisicao
     */
    private Status status;
    private String msg;
    private Object data;
    private Boolean hasError;
    private List<String> errorList = new ArrayList<String>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getHasError() {
        return hasError;
    }

    public void setHasError(Boolean hasError) {
        this.hasError = hasError;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public void addError(String errorMsg) {
        this.errorList.add(errorMsg);
    }
}