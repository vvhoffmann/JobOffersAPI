package com.hoffmann.joboffersapi.domain.offercrud;

import lombok.Builder;

@Builder
record Offer (String id,
              String companyName,
              String position,
              String salary,
              String offerUrl){
}