package com.spodfy.resource.config;

import com.spodfy.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.math.BigDecimal;
import java.text.*;
import java.util.Date;
import java.util.Locale;

/**
 * Classe criada para gerenciar transacoes http, recuperar e tratar erros
 *
 * @author pgsilva
 */
public abstract class BaseResource {

    public static final String GENERIC_ERROR = "Ocorreu um erro inesperado ao processar sua solicitação. Tente novamente mais tarde.";

    /*
     * Media Types
     */
    protected static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    protected static final Locale LOCALE = LocaleContextHolder.getLocale();

    @Autowired
    private MessageSource resource;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        CustomDateEditor dateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, dateEditor);

        NumberFormat nf = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        CustomNumberEditor numberEditor = new CustomNumberEditor(BigDecimal.class, nf, true);
        binder.registerCustomEditor(BigDecimal.class, numberEditor);

        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }


    protected AjaxResult buildAjaxSuccessResult(Object data) {
        AjaxResult result = new AjaxResult();
        result.setData(data);
        result.setHasError(false);
        result.setMsg("ok");
        result.setStatus(AjaxResult.Status.SUCESSO);

        return result;
    }

    protected AjaxResult buildAjaxErrorResult(Exception e) {
        AjaxResult result = new AjaxResult();
        result.setHasError(true);
        result.setStatus(AjaxResult.Status.ERRO);

        result.addError(GENERIC_ERROR);
        result.setMsg(e.getMessage());

        return result;
    }

    protected String getMsg(String key) {

        try {
            return resource.getMessage(key, null, LOCALE);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    protected String getMsg(String key, Object... params) {
        try {
            return MessageFormat.format(resource.getMessage(key, null, LOCALE), params);
        } catch (NoSuchMessageException e) {
            return null;
        }
    }

    /**
     * Retorna string de redirect para ser direcionado como comandio
     *
     * @param uri
     * @return
     */
    protected String redirect(String uri) {
        return "redirect:" + uri;
    }

}
