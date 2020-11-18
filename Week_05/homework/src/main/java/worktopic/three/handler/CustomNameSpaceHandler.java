package worktopic.three.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import worktopic.three.parser.KlassBeanDefinitionParser;
import worktopic.three.parser.SchoolBeanDefinitionParser;
import worktopic.three.parser.StudentBeanDefinitionParser;

/**
 * StudentNameSpaceHandler
 *
 * @author qrXun on 2020/11/14
 */
public class CustomNameSpaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
        registerBeanDefinitionParser("klass", new KlassBeanDefinitionParser());
        registerBeanDefinitionParser("school", new SchoolBeanDefinitionParser());
    }
}
