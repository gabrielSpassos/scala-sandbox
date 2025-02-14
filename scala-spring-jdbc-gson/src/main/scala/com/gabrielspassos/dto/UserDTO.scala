package com.gabrielspassos.dto

import java.util.UUID

case class UserDTO(
  id: UUID,
  banks: List[BankDTO],
  wallets: List[WalletDTO],
)

case class WalletDTO(
  id: UUID,
  name: String,
  cards: List[CardDTO],
)
