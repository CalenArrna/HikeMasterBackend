package org.backend.Model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHikeMasterUser is a Querydsl query type for HikeMasterUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHikeMasterUser extends EntityPathBase<HikeMasterUser> {

    private static final long serialVersionUID = 460101969L;

    public static final QHikeMasterUser hikeMasterUser = new QHikeMasterUser("hikeMasterUser");

    public final SetPath<Authority, QAuthority> authorities = this.<Authority, QAuthority>createSet("authorities", Authority.class, QAuthority.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath fullName = createString("fullName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeactivated = createBoolean("isDeactivated");

    public final BooleanPath notification = createBoolean("notification");

    public final StringPath password = createString("password");

    public final StringPath role = createString("role");

    public final ListPath<Messages, QMessages> UserMessagesList = this.<Messages, QMessages>createList("UserMessagesList", Messages.class, QMessages.class, PathInits.DIRECT2);

    public final StringPath username = createString("username");

    public QHikeMasterUser(String variable) {
        super(HikeMasterUser.class, forVariable(variable));
    }

    public QHikeMasterUser(Path<? extends HikeMasterUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHikeMasterUser(PathMetadata metadata) {
        super(HikeMasterUser.class, metadata);
    }

}

