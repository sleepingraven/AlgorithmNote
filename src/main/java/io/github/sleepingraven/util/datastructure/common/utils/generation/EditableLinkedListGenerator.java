package io.github.sleepingraven.util.datastructure.common.utils.generation;

import io.github.sleepingraven.util.datastructure.linkedlist.EditableLinkedList;

import java.util.function.Supplier;

/**
 * 根据 V 数组生成 {@link EditableLinkedList}
 *
 * @author Carry
 * @since 2020/6/18
 */
public class EditableLinkedListGenerator<T extends EditableLinkedList<T, V>, V>
        extends RelinkableLinkedListGenerator<T, V> {
    
    public EditableLinkedListGenerator(Supplier<? extends T> factory) {
        super(factory, EditableLinkedList::setValue);
    }
    
}
