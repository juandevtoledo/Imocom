/*
 * Copyright (c) 2014 IMOCOM. All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of IMOCOM.
 * ("Confidential Information").
 * It may not be copied or reproduced in any manner without the express
 * written permission of IMOCOM.
 */
package com.imocom.intelcom.view.beans;

import java.util.List;
import java.util.Objects;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author juan.toledo
 */
@FacesConverter("selectOneMenuConverter")
public class SelectOneMenuConverter implements Converter {

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }

        return fromSelect(component, value);
    }

    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return serialize(o);
    }

    private String serialize(final Object object) {
        if (object == null) {
            return null;
        }
        return object.getClass() + "@" + object.hashCode();
    }

    private Object fromSelect(final UIComponent currentcomponent, final String objectString) {

        if (currentcomponent.getClass() == UISelectItem.class) {
            final UISelectItem item = (UISelectItem) currentcomponent;
            final Object value = item.getValue();
            if (objectString.equals(serialize(value))) {
                return value;
            }
        }

        if (currentcomponent.getClass() == UISelectItems.class) {
            final UISelectItems items = (UISelectItems) currentcomponent;
            final List<Object> elements = (List<Object>) items.getValue();            
            if (elements != null) {
                for (final Object element : elements) {
                    if (objectString.equals(serialize(element))) {
                        return element;
                    }
                }
            }
        }

        if (!currentcomponent.getChildren().isEmpty()) {
            for (final UIComponent component : currentcomponent.getChildren()) {
                final Object result = fromSelect(component, objectString);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}
