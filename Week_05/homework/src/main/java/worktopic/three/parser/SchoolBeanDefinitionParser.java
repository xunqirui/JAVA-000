package worktopic.three.parser;

import common.entity.Klass;
import common.entity.School;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.ObjectUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * SchoolBeanDefinitionParser
 *
 * @author qrXun on 2020/11/15
 */
public class SchoolBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected Class<?> getBeanClass(Element element) {
        return School.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        BeanDefinitionRegistry registry = parserContext.getRegistry();
        NodeList nodeList = element.getChildNodes();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(ListFactoryBean.class);
        ManagedList<RuntimeBeanReference> managedList = new ManagedList<>();
        for (int i = 0; i<nodeList.getLength();i++){
            Node node = nodeList.item(i);
            NamedNodeMap nodeMap = node.getAttributes();
            if (!ObjectUtils.isEmpty(nodeMap)){
                Node refNode = nodeMap.getNamedItem("ref");
                Node nameNode = nodeMap.getNamedItem("name");
                String[] refNameArray = refNode.getNodeValue().split(",");
                for (String refName : refNameArray){
                    RuntimeBeanReference reference = new RuntimeBeanReference(refName);
                    managedList.add(reference);
                }
                beanDefinitionBuilder.addPropertyValue("sourceList", managedList);
                registry.registerBeanDefinition("classList#" + i, beanDefinitionBuilder.getBeanDefinition());
                builder.addPropertyReference(nameNode.getNodeValue(), "classList#" + i);

            }
        }
    }
}
