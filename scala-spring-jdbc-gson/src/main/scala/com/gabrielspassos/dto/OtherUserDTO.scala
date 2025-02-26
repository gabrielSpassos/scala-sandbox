package com.gabrielspassos.dto

import com.google.gson.annotations.SerializedName

import java.util.UUID
import scala.annotation.meta.field

case class OtherUserDTO(
  id: UUID,
  @(SerializedName @field)(value = "bank_list") banks: List[BankDTO],
  @(SerializedName @field)(value = "wallet_list") wallets: List[OtherWalletDTO],
)

case class OtherWalletDTO(
  id: UUID,
  name: String,
  @(SerializedName @field)(value = "card_list") cards: List[CardDTO],
)
