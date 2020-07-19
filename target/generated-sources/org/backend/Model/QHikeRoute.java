package org.backend.Model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHikeRoute is a Querydsl query type for HikeRoute
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QHikeRoute extends EntityPathBase<HikeRoute> {

    private static final long serialVersionUID = -711807035L;

    public static final QHikeRoute hikeRoute = new QHikeRoute("hikeRoute");

    public final NumberPath<Double> coordinates = createNumber("coordinates", Double.class);

    public final StringPath difficulty = createString("difficulty");

    public final NumberPath<Double> distanceFromLoc = createNumber("distanceFromLoc", Double.class);

    public final NumberPath<Integer> levelRise = createNumber("levelRise", Integer.class);

    public final ListPath<Messages, QMessages> messages = this.<Messages, QMessages>createList("messages", Messages.class, QMessages.class, PathInits.DIRECT2);

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public final NumberPath<Long> routeId = createNumber("routeId", Long.class);

    public final StringPath routeType = createString("routeType");

    public final NumberPath<Integer> tourLength = createNumber("tourLength", Integer.class);

    public final StringPath tourType = createString("tourType");

    public QHikeRoute(String variable) {
        super(HikeRoute.class, forVariable(variable));
    }

    public QHikeRoute(Path<? extends HikeRoute> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHikeRoute(PathMetadata metadata) {
        super(HikeRoute.class, metadata);
    }

}

