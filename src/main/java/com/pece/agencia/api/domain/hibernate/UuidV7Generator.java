package com.pece.agencia.api.domain.hibernate;

import com.github.f4b6a3.uuid.UuidCreator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public final class UuidV7Generator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        // Gera um UUID temporal/ordenável (uuid-creator fornece suportes para UUIDs baseados em tempo)
        UUID uuid = UuidCreator.getTimeOrdered();
        return uuid;
    }
}

