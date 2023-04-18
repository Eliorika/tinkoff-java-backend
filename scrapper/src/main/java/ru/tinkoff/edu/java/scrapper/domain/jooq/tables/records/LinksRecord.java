/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records;


import jakarta.validation.constraints.Size;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

import javax.annotation.processing.Generated;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class LinksRecord extends UpdatableRecordImpl<LinksRecord> implements Record3<Long, String, LocalDateTime> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>LINKS.LINK_ID</code>.
     */
    public void setLinkId(@NotNull Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>LINKS.LINK_ID</code>.
     */
    @NotNull
    public Long getLinkId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>LINKS.LINK</code>.
     */
    public void setLink(@NotNull String value) {
        set(1, value);
    }

    /**
     * Getter for <code>LINKS.LINK</code>.
     */
    @jakarta.validation.constraints.NotNull
    @Size(max = 255)
    @NotNull
    public String getLink() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LINKS.LAST_CHECKED</code>.
     */
    public void setLastChecked(@NotNull LocalDateTime value) {
        set(2, value);
    }

    /**
     * Getter for <code>LINKS.LAST_CHECKED</code>.
     */
    @jakarta.validation.constraints.NotNull
    @NotNull
    public LocalDateTime getLastChecked() {
        return (LocalDateTime) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row3<Long, String, LocalDateTime> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    @NotNull
    public Row3<Long, String, LocalDateTime> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    @NotNull
    public Field<Long> field1() {
        return Links.LINKS.LINK_ID;
    }

    @Override
    @NotNull
    public Field<String> field2() {
        return Links.LINKS.LINK;
    }

    @Override
    @NotNull
    public Field<LocalDateTime> field3() {
        return Links.LINKS.LAST_CHECKED;
    }

    @Override
    @NotNull
    public Long component1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String component2() {
        return getLink();
    }

    @Override
    @NotNull
    public LocalDateTime component3() {
        return getLastChecked();
    }

    @Override
    @NotNull
    public Long value1() {
        return getLinkId();
    }

    @Override
    @NotNull
    public String value2() {
        return getLink();
    }

    @Override
    @NotNull
    public LocalDateTime value3() {
        return getLastChecked();
    }

    @Override
    @NotNull
    public LinksRecord value1(@NotNull Long value) {
        setLinkId(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value2(@NotNull String value) {
        setLink(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord value3(@NotNull LocalDateTime value) {
        setLastChecked(value);
        return this;
    }

    @Override
    @NotNull
    public LinksRecord values(@NotNull Long value1, @NotNull String value2, @NotNull LocalDateTime value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached LinksRecord
     */
    public LinksRecord() {
        super(Links.LINKS);
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    @ConstructorProperties({ "linkId", "link", "lastChecked" })
    public LinksRecord(@NotNull Long linkId, @NotNull String link, @NotNull LocalDateTime lastChecked) {
        super(Links.LINKS);

        setLinkId(linkId);
        setLink(link);
        setLastChecked(lastChecked);
    }

    /**
     * Create a detached, initialised LinksRecord
     */
    public LinksRecord(ru.tinkoff.edu.java.scrapper.domain.jooq.tables.pojos.Links value) {
        super(Links.LINKS);

        if (value != null) {
            setLinkId(value.getLinkId());
            setLink(value.getLink());
            setLastChecked(value.getLastChecked());
        }
    }
}
