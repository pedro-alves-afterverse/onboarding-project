package com.playkids.onboarding.core.util

interface Currencies

enum class ItemCurrencies: Currencies{
    COIN {
        override fun toString(): String {
            return "coin"
        }
    },
    GEM {
        override fun toString(): String {
            return "gem"
        }
    };
}

enum class ProfileCurrencies: Currencies{
    COIN {
        override fun toString(): String {
            return "coin"
        }
    },
    GEM {
        override fun toString(): String {
            return "gem"
        }
    },
    MONEY {
        override fun toString(): String {
            return "moneySpent"
        }
    }
}