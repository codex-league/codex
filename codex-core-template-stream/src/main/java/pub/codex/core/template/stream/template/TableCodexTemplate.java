package pub.codex.core.template.stream.template;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import pub.codex.common.CodexException;
import pub.codex.common.db.entity.TableEntity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * TABLE相关的 实现的抽象类
 * 根据 数据库表 coding 的template 写这里
 *
 * @author xuxi
 */


public abstract class TableCodexTemplate extends BaseTableCodexTemplate {

    /**
     * 表信息
     */
    protected TableEntity tableEntity;

    /**
     * 压缩包信息
     */
    protected ZipOutputStream zip;

    /**
     * coding 的主要逻辑在这里实现
     */
    public abstract void coding();

    /**
     * build 信息
     */
    public void build(TableEntity tableEntity, ZipOutputStream zip){
        this.tableEntity = tableEntity;
        this.zip = zip;
        coding();
    }

    /**
     * 渲染模板
     *
     * @param templateName
     * @param map
     * @param filepath
     */
    protected void buildTemplate(String templateName, Map<String, Object> map, String filepath) {
        //渲染模板
        Template template = getTemplate(templateName);
        VelocityContext context = new VelocityContext(map);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        //添加到zip
        try {
            zip.putNextEntry(new ZipEntry(filepath));
            IOUtils.write(sw.toString(), zip, "UTF-8" );
            IOUtils.closeQuietly(sw);
            zip.closeEntry();
        } catch (IOException e) {
            throw new CodexException("渲染模板失败，表名：" + tableEntity.getClassName(), e);
        }
    }

}
