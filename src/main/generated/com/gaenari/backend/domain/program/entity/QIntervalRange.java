package com.gaenari.backend.domain.program.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QIntervalRange is a Querydsl query type for IntervalRange
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIntervalRange extends EntityPathBase<IntervalRange> {

    private static final long serialVersionUID = -1750657951L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QIntervalRange intervalRange = new QIntervalRange("intervalRange");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRunning = createBoolean("isRunning");

    public final QProgram program;

    public final NumberPath<Integer> speed = createNumber("speed", Integer.class);

    public final NumberPath<Integer> time = createNumber("time", Integer.class);

    public QIntervalRange(String variable) {
        this(IntervalRange.class, forVariable(variable), INITS);
    }

    public QIntervalRange(Path<? extends IntervalRange> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QIntervalRange(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QIntervalRange(PathMetadata metadata, PathInits inits) {
        this(IntervalRange.class, metadata, inits);
    }

    public QIntervalRange(Class<? extends IntervalRange> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.program = inits.isInitialized("program") ? new QProgram(forProperty("program")) : null;
    }

}

