/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq;


import javax.annotation.processing.Generated;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;

import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Chats;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.Links;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.LinksList;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.ChatsRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksListRecord;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.LinksRecord;


/**
 * A class modelling foreign key relationships and constraints of tables in the
 * default schema.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.17.6"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<ChatsRecord> CONSTRAINT_3 = Internal.createUniqueKey(Chats.CHATS, DSL.name("CONSTRAINT_3"), new TableField[] { Chats.CHATS.TG_CHAT }, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_4 = Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_4"), new TableField[] { Links.LINKS.LINK_ID }, true);
    public static final UniqueKey<LinksRecord> CONSTRAINT_45 = Internal.createUniqueKey(Links.LINKS, DSL.name("CONSTRAINT_45"), new TableField[] { Links.LINKS.LINK }, true);
    public static final UniqueKey<LinksListRecord> CONSTRAINT_8 = Internal.createUniqueKey(LinksList.LINKS_LIST, DSL.name("CONSTRAINT_8"), new TableField[] { LinksList.LINKS_LIST.TRACK_ID }, true);
    public static final UniqueKey<LinksListRecord> UN_TRACK = Internal.createUniqueKey(LinksList.LINKS_LIST, DSL.name("UN_TRACK"), new TableField[] { LinksList.LINKS_LIST.LINK_ID, LinksList.LINKS_LIST.TG_CHAT }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<LinksListRecord, LinksRecord> CONSTRAINT_86 = Internal.createForeignKey(LinksList.LINKS_LIST, DSL.name("CONSTRAINT_86"), new TableField[] { LinksList.LINKS_LIST.LINK_ID }, Keys.CONSTRAINT_4, new TableField[] { Links.LINKS.LINK_ID }, true);
    public static final ForeignKey<LinksListRecord, ChatsRecord> CONSTRAINT_866 = Internal.createForeignKey(LinksList.LINKS_LIST, DSL.name("CONSTRAINT_866"), new TableField[] { LinksList.LINKS_LIST.TG_CHAT }, Keys.CONSTRAINT_3, new TableField[] { Chats.CHATS.TG_CHAT }, true);
}
