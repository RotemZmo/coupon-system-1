export interface Coupon {
  id?: number
  title?: string
  startDate?: string
  endDate?: string
  amount?: number
  couponType?: string
  message?: string
  price?: number
  companyId?: number
}

export interface CouponType {
  value: string;
}

