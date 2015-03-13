/*
 * Part of Simbrain--a java-based neural network kit
 * Copyright (C) 2005,2007 The Authors.  See http://www.simbrain.net/credits
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.simbrain.world.textworld;

import org.simbrain.world.textworld.TextWorld.TextItem;

/**
 * Listen for changes in the text world.
 */
public interface TextListener {

    /**
     * The text has changed.
     */
    void textChanged();

    /**
     * The dictionary has changed.
     */
    void dictionaryChanged();

    /**
     * The position of the caret has changed.
     */
    void positionChanged();

    /**
     * The current item has changed.
     *
     * @param newItem the new current text item.
     */
    void currentItemChanged(TextItem newItem);

    /**
     * The current preferences have changed.
     */
    void preferencesChanged();

    /**
     * Adapter class so users of the interface don't have to implement every
     * method.
     */
    public class TextAdapter implements TextListener {

        @Override
        public void textChanged() {
        }

        @Override
        public void dictionaryChanged() {
        }

        @Override
        public void positionChanged() {
        }

        @Override
        public void currentItemChanged(TextItem newItem) {
        }

        @Override
        public void preferencesChanged() {
        }

    }

}
