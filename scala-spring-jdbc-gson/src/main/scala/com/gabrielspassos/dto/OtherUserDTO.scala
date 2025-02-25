package com.gabrielspassos.dto

import com.google.gson.annotations.SerializedName

import java.util.UUID

case class OtherUserDTO(
  id: UUID,
  @SerializedName(value = "bank_list") banks: List[BankDTO],
  @SerializedName("wallet_list") wallets: List[OtherWalletDTO],
)

case class OtherWalletDTO(
  id: UUID,
  name: String,
  @SerializedName("card_list") cards: List[CardDTO],
)
