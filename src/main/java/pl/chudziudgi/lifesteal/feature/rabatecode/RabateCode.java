package pl.chudziudgi.lifesteal.feature.rabatecode;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class RabateCode implements Serializable {

    private final String code;
    private final String commandToExeciute;

    public RabateCode(String code, String commandToExeciute) {
        this.code = code;
        this.commandToExeciute = commandToExeciute;
    }
}
