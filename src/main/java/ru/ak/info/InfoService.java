package ru.ak.info;

import ru.ak.importtable.ParseService;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Корневой Web-сервис, содержащий метод получения версии
 * @author akakushin
 */

@WebService(name = "Info", serviceName = "Info", portName = "InfoPort")
public class InfoService extends ParseService {

    /**
     * Получение версии компоненты
     * @return Версия компоненты
     */
    @WebMethod(operationName = "version")
    public String version() {
        return version_1_0_0_2();
    }

    private String version_1_0_0_2() {
        /* Оформление файла pom.xml */
        return "1.0.0.2";
    }

    private String version_1_0_0_1() {
        // Инициализация проекта
        return "1.0.0.1";
    }
}
