package com.trodix.migration.core.readers;

import org.springframework.batch.item.ItemReader;

/**
 * Permet de faire plusieurs actions dans un reader, comme requêter une autre source de données.
 */
public interface ItemMultipleSourceReader<T> extends ItemReader<T> {

    /**
     * <p>
     * Execute une nouvelle action sur l'Item du reader courant.
     * </p>
     * 
     * <p>
     * Cette methode ne doit <b>JAMAIS</b> renvoyer <code>null</code>, sinon le traitement du Job
     * s'arrête en état <i>COMPLETED</i> au moment où <code>null</code> est retourné.
     * </p>
     * 
     * @param curentItem L'item courant du reader
     * @return L'item mis à jour via l'action'
     */
    public T handleNextSource(T curentItem);

}
