package pub.codex.core.template.stream;

import org.springframework.stereotype.Service;

/**
 * 项目模板基础信息
 */
public interface BaseTemplateConfigProvider {


    /**
     * 获取Service包路径
     *
     * @return
     */
    String getServicePath();

    /**
     * 获取ServiceImpl包路径
     *
     * @return
     */
    String getServiceImplPath();

    /**
     * 获取Mapper包路径
     *
     * @return
     */
    String getMapperPath();

    /**
     * 获取MapperXML包路径
     *
     * @return
     */
    String getMapperXMLPath();

    /**
     * 获取entity包路径
     *
     * @return
     */
    String getEntityPath();


}
