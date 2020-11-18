package worktopic.three.parser;

import common.entity.Student;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * StudentBeanDefinitionParser
 *
 * @author qrXun on 2020/11/14
 */
public class StudentBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return Student.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        // 获取子 Node
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            NamedNodeMap nodeMap = node.getAttributes();
            if (!ObjectUtils.isEmpty(nodeMap)){
                String name = nodeMap.item(0).getNodeValue();
                String value = nodeMap.item(1).getNodeValue();
                builder.addPropertyValue(name, value);
            }
        }
    }

}
