package com.lebastudios.stexteditor.nodes.formateableText;

import com.sun.javafx.collections.ListListenerHelper;
import com.sun.javafx.collections.NonIterableChange;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.*;
import javafx.css.converter.SizeConverter;
import javafx.scene.AccessibleRole;
import javafx.scene.control.*;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.text.Text;

import java.util.*;

public class EditableTextLine extends TextInputControl
{
    TextField textField = new TextField(new Text("").toString());
    public static final int DEFAULT_PREF_COLUMN_COUNT = 40;
    public static final int DEFAULT_PREF_ROW_COUNT = 10;
    private static final int DEFAULT_PARAGRAPH_CAPACITY = 32;
    private BooleanProperty wrapText;
    private IntegerProperty prefColumnCount;
    private IntegerProperty prefRowCount;
    private DoubleProperty scrollTop;
    private DoubleProperty scrollLeft;

    public TextArea() {
        this("");
    }

    public TextArea(String var1) {
        super(new TextArea.TextAreaContent());
        this.wrapText = new StyleableBooleanProperty(false) {
            public Object getBean() {
                return TextArea.this;
            }

            public String getName() {
                return "wrapText";
            }

            public CssMetaData<TextArea, Boolean> getCssMetaData() {
                return TextArea.StyleableProperties.WRAP_TEXT;
            }
        };
        this.prefColumnCount = new StyleableIntegerProperty(40) {
            private int oldValue = this.get();

            protected void invalidated() {
                int var1 = this.get();
                if (var1 < 0) {
                    if (this.isBound()) {
                        this.unbind();
                    }

                    this.set(this.oldValue);
                    throw new IllegalArgumentException("value cannot be negative.");
                } else {
                    this.oldValue = var1;
                }
            }

            public CssMetaData<TextArea, Number> getCssMetaData() {
                return TextArea.StyleableProperties.PREF_COLUMN_COUNT;
            }

            public Object getBean() {
                return TextArea.this;
            }

            public String getName() {
                return "prefColumnCount";
            }
        };
        this.prefRowCount = new StyleableIntegerProperty(10) {
            private int oldValue = this.get();

            protected void invalidated() {
                int var1 = this.get();
                if (var1 < 0) {
                    if (this.isBound()) {
                        this.unbind();
                    }

                    this.set(this.oldValue);
                    throw new IllegalArgumentException("value cannot be negative.");
                } else {
                    this.oldValue = var1;
                }
            }

            public CssMetaData<TextArea, Number> getCssMetaData() {
                return TextArea.StyleableProperties.PREF_ROW_COUNT;
            }

            public Object getBean() {
                return TextArea.this;
            }

            public String getName() {
                return "prefRowCount";
            }
        };
        this.scrollTop = new SimpleDoubleProperty(this, "scrollTop", 0.0);
        this.scrollLeft = new SimpleDoubleProperty(this, "scrollLeft", 0.0);
        this.getStyleClass().add("text-area");
        this.setAccessibleRole(AccessibleRole.TEXT_AREA);
        this.setText(var1);
    }

    final void textUpdated() {
        this.setScrollTop(0.0);
        this.setScrollLeft(0.0);
    }

    public ObservableList<CharSequence> getParagraphs() {
        return ((TextArea.TextAreaContent)this.getContent()).paragraphList;
    }

    public final BooleanProperty wrapTextProperty() {
        return this.wrapText;
    }

    public final boolean isWrapText() {
        return this.wrapText.getValue();
    }

    public final void setWrapText(boolean var1) {
        this.wrapText.setValue(var1);
    }

    public final IntegerProperty prefColumnCountProperty() {
        return this.prefColumnCount;
    }

    public final int getPrefColumnCount() {
        return this.prefColumnCount.getValue();
    }

    public final void setPrefColumnCount(int var1) {
        this.prefColumnCount.setValue(var1);
    }

    public final IntegerProperty prefRowCountProperty() {
        return this.prefRowCount;
    }

    public final int getPrefRowCount() {
        return this.prefRowCount.getValue();
    }

    public final void setPrefRowCount(int var1) {
        this.prefRowCount.setValue(var1);
    }

    public final DoubleProperty scrollTopProperty() {
        return this.scrollTop;
    }

    public final double getScrollTop() {
        return this.scrollTop.getValue();
    }

    public final void setScrollTop(double var1) {
        this.scrollTop.setValue(var1);
    }

    public final DoubleProperty scrollLeftProperty() {
        return this.scrollLeft;
    }

    public final double getScrollLeft() {
        return this.scrollLeft.getValue();
    }

    public final void setScrollLeft(double var1) {
        this.scrollLeft.setValue(var1);
    }

    protected Skin<?> createDefaultSkin() {
        return new TextAreaSkin(this);
    }

    String filterInput(String var1) {
        return TextInputControl.filterInput(var1, false, false);
    }

    public static List<CssMetaData<? extends Styleable, ?>> getClassCssMetaData() {
        return TextArea.StyleableProperties.STYLEABLES;
    }

    public List<CssMetaData<? extends Styleable, ?>> getControlCssMetaData() {
        return getClassCssMetaData();
    }

    private static final class TextAreaContent extends TextInputControl.ContentBase {
        private final List<StringBuilder> paragraphs = new ArrayList();
        private final TextArea.ParagraphList paragraphList = new TextArea.ParagraphList();
        private int contentLength = 0;

        private TextAreaContent() {
            this.paragraphs.add(new StringBuilder(32));
            this.paragraphList.content = this;
        }

        public String get(int var1, int var2) {
            int var3 = var2 - var1;
            StringBuilder var4 = new StringBuilder(var3);
            int var5 = this.paragraphs.size();
            int var6 = 0;

            int var7;
            StringBuilder var8;
            int var9;
            for(var7 = var1; var6 < var5; ++var6) {
                var8 = (StringBuilder)this.paragraphs.get(var6);
                var9 = var8.length() + 1;
                if (var7 < var9) {
                    break;
                }

                var7 -= var9;
            }

            var8 = (StringBuilder)this.paragraphs.get(var6);

            for(var9 = 0; var9 < var3; ++var9) {
                if (var7 == var8.length() && var9 < this.contentLength) {
                    var4.append('\n');
                    ++var6;
                    var8 = (StringBuilder)this.paragraphs.get(var6);
                    var7 = 0;
                } else {
                    var4.append(var8.charAt(var7++));
                }
            }

            return var4.toString();
        }

        public void insert(int var1, String var2, boolean var3) {
            if (var1 >= 0 && var1 <= this.contentLength) {
                if (var2 == null) {
                    throw new IllegalArgumentException();
                } else {
                    var2 = TextInputControl.filterInput(var2, false, false);
                    int var4 = var2.length();
                    if (var4 > 0) {
                        ArrayList var5 = new ArrayList();
                        StringBuilder var6 = new StringBuilder(32);

                        int var7;
                        int var8;
                        for(var7 = 0; var7 < var4; ++var7) {
                            var8 = var2.charAt(var7);
                            if (var8 == 10) {
                                var5.add(var6);
                                var6 = new StringBuilder(32);
                            } else {
                                var6.append((char)var8);
                            }
                        }

                        var5.add(var6);
                        var7 = this.paragraphs.size();
                        var8 = this.contentLength + 1;
                        StringBuilder var9 = null;

                        do {
                            --var7;
                            var9 = (StringBuilder)this.paragraphs.get(var7);
                            var8 -= var9.length() + 1;
                        } while(var1 < var8);

                        int var10 = var1 - var8;
                        int var11 = var5.size();
                        if (var11 == 1) {
                            var9.insert(var10, var6);
                            this.fireParagraphListChangeEvent(var7, var7 + 1, Collections.singletonList(var9));
                        } else {
                            int var12 = var9.length();
                            CharSequence var13 = var9.subSequence(var10, var12);
                            var9.delete(var10, var12);
                            StringBuilder var14 = (StringBuilder)var5.get(0);
                            var9.insert(var10, var14);
                            var6.append(var13);
                            this.fireParagraphListChangeEvent(var7, var7 + 1, Collections.singletonList(var9));
                            this.paragraphs.addAll(var7 + 1, var5.subList(1, var11));
                            this.fireParagraphListChangeEvent(var7 + 1, var7 + var11, Collections.EMPTY_LIST);
                        }

                        this.contentLength += var4;
                        if (var3) {
                            this.fireValueChangedEvent();
                        }
                    }

                }
            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public void delete(int var1, int var2, boolean var3) {
            if (var1 > var2) {
                throw new IllegalArgumentException();
            } else if (var1 >= 0 && var2 <= this.contentLength) {
                int var4 = var2 - var1;
                if (var4 > 0) {
                    int var5 = this.paragraphs.size();
                    int var6 = this.contentLength + 1;
                    StringBuilder var7 = null;

                    do {
                        --var5;
                        var7 = (StringBuilder)this.paragraphs.get(var5);
                        var6 -= var7.length() + 1;
                    } while(var2 < var6);

                    int var8 = var5;
                    int var9 = var6;
                    StringBuilder var10 = var7;
                    ++var5;
                    var6 += var7.length() + 1;

                    do {
                        --var5;
                        var7 = (StringBuilder)this.paragraphs.get(var5);
                        var6 -= var7.length() + 1;
                    } while(var1 < var6);

                    if (var5 == var8) {
                        var7.delete(var1 - var6, var2 - var6);
                        this.fireParagraphListChangeEvent(var5, var5 + 1, Collections.singletonList(var7));
                    } else {
                        CharSequence var14 = var7.subSequence(0, var1 - var6);
                        int var15 = var1 + var4 - var9;
                        var10.delete(0, var15);
                        this.fireParagraphListChangeEvent(var8, var8 + 1, Collections.singletonList(var10));
                        if (var8 - var5 > 0) {
                            ArrayList var16 = new ArrayList(this.paragraphs.subList(var5, var8));
                            this.paragraphs.subList(var5, var8).clear();
                            this.fireParagraphListChangeEvent(var5, var5, var16);
                        }

                        var10.insert(0, var14);
                        this.fireParagraphListChangeEvent(var5, var5 + 1, Collections.singletonList(var7));
                    }

                    this.contentLength -= var4;
                    if (var3) {
                        this.fireValueChangedEvent();
                    }
                }

            } else {
                throw new IndexOutOfBoundsException();
            }
        }

        public int length() {
            return this.contentLength;
        }

        public String get() {
            return this.get(0, this.length());
        }

        public String getValue() {
            return this.get();
        }

        private void fireParagraphListChangeEvent(int var1, int var2, List<CharSequence> var3) {
            TextArea.ParagraphListChange var4 = new TextArea.ParagraphListChange(this.paragraphList, var1, var2, var3);
            ListListenerHelper.fireValueChangedEvent(this.paragraphList.listenerHelper, var4);
        }
    }

    private static final class ParagraphList extends AbstractList<CharSequence> implements ObservableList<CharSequence> {
        private TextArea.TextAreaContent content;
        private ListListenerHelper<CharSequence> listenerHelper;

        private ParagraphList() {
        }

        public CharSequence get(int var1) {
            return (CharSequence)this.content.paragraphs.get(var1);
        }

        public boolean addAll(Collection<? extends CharSequence> var1) {
            throw new UnsupportedOperationException();
        }

        public boolean addAll(CharSequence... var1) {
            throw new UnsupportedOperationException();
        }

        public boolean setAll(Collection<? extends CharSequence> var1) {
            throw new UnsupportedOperationException();
        }

        public boolean setAll(CharSequence... var1) {
            throw new UnsupportedOperationException();
        }

        public int size() {
            return this.content.paragraphs.size();
        }

        public void addListener(ListChangeListener<? super CharSequence> var1) {
            this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, var1);
        }

        public void removeListener(ListChangeListener<? super CharSequence> var1) {
            this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, var1);
        }

        public boolean removeAll(CharSequence... var1) {
            throw new UnsupportedOperationException();
        }

        public boolean retainAll(CharSequence... var1) {
            throw new UnsupportedOperationException();
        }

        public void remove(int var1, int var2) {
            throw new UnsupportedOperationException();
        }

        public void addListener(InvalidationListener var1) {
            this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, var1);
        }

        public void removeListener(InvalidationListener var1) {
            this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, var1);
        }
    }

    private static class StyleableProperties {
        private static final CssMetaData<TextArea, Number> PREF_COLUMN_COUNT = new CssMetaData<TextArea, Number>("-fx-pref-column-count", SizeConverter.getInstance(), 40) {
            public boolean isSettable(TextArea var1) {
                return !var1.prefColumnCount.isBound();
            }

            public StyleableProperty<Number> getStyleableProperty(TextArea var1) {
                return (StyleableProperty)var1.prefColumnCountProperty();
            }
        };
        private static final CssMetaData<TextArea, Number> PREF_ROW_COUNT = new CssMetaData<TextArea, Number>("-fx-pref-row-count", SizeConverter.getInstance(), 10) {
            public boolean isSettable(TextArea var1) {
                return !var1.prefRowCount.isBound();
            }

            public StyleableProperty<Number> getStyleableProperty(TextArea var1) {
                return (StyleableProperty)var1.prefRowCountProperty();
            }
        };
        private static final CssMetaData<TextArea, Boolean> WRAP_TEXT = new CssMetaData<TextArea, Boolean>("-fx-wrap-text", StyleConverter.getBooleanConverter(), false) {
            public boolean isSettable(TextArea var1) {
                return !var1.wrapText.isBound();
            }

            public StyleableProperty<Boolean> getStyleableProperty(TextArea var1) {
                return (StyleableProperty)var1.wrapTextProperty();
            }
        };
        private static final List<CssMetaData<? extends Styleable, ?>> STYLEABLES;

        private StyleableProperties() {
        }

        static {
            ArrayList var0 = new ArrayList(TextInputControl.getClassCssMetaData());
            var0.add(PREF_COLUMN_COUNT);
            var0.add(PREF_ROW_COUNT);
            var0.add(WRAP_TEXT);
            STYLEABLES = Collections.unmodifiableList(var0);
        }
    }

    private static final class ParagraphListChange extends NonIterableChange<CharSequence>
    {
        private List<CharSequence> removed;

        protected ParagraphListChange(ObservableList<CharSequence> var1, int var2, int var3, List<CharSequence> var4) {
            super(var2, var3, var1);
            this.removed = var4;
        }

        public List<CharSequence> getRemoved() {
            return this.removed;
        }

        protected int[] getPermutation() {
            return new int[0];
        }
    }
}