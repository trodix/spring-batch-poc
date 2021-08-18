package com.trodix.migration.core.readers;

import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ListItemReader<T> implements ItemReader<T> {

    private Integer nextItemIndex;
    private List<T> dataProvider;
    private List<T> itemData;

    public ListItemReader(List<T> dataProvider) {
        super();
        nextItemIndex = 0;
        this.dataProvider = dataProvider;
    }

    public List<T> getDataProvider() {
        return dataProvider;
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {

        if (itemData == null) {
            itemData = this.getDataProvider();
        }

        T nextItem = null;

        if (nextItemIndex < itemData.size()) {
            nextItem = itemData.get(nextItemIndex);
            nextItemIndex++;
        } else {
            nextItemIndex = 0;
            nextItem = null;
        }

        return nextItem;
    }

}
